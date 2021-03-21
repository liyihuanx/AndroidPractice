package liyihuan.app.android.mrouter_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import liyihuan.app.android.mrouter_annotation.Parameter;


/**
 * @ClassName: ParameterProcessor
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/3/21 21:23
 */

@AutoService(Processor.class)
@SupportedAnnotationTypes({ProcessorConfig.PARAMETER})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParameterProcessor extends AbstractProcessor {

    private Elements elementTool; // 类信息
    private Types typeTool; // 具体类型
    private Messager messager; // 日志
    private Filer filer; // 生成文件

    // 临时map存储，用来存放被@Parameter注解的属性集合，生成类文件时遍历
    // key:类节点(Activity), value:被@Parameter注解的属性集合(里面的变量)
    private Map<TypeElement, List<Element>> parameterMap = new HashMap<>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();

        // 打印
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>> init <<<<<<<<<");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>> process <<<<<<<<<");
        if (set.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "没有使用过Parameter注解");
            return false;
        }

        /**
         * 模板
         * public class Order_MainActivity$$Parameter implements ParameterGet {
         *   @Override
         *   public void getParameter(Object targetParameter) {
         *     Order_MainActivity t = (Order_MainActivity) targetParameter;
         *     t.name = t.getIntent().getStringExtra("name");
         *   }
         * }
         */

        // 获取所有被 @Parameter 注解的 元素集合 == 变量的element信息
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Parameter.class);
        for (Element element : elements) { // 遍历取出Activity里面用了注解的参数

            // 获取使用了注解变量的类 == Activity
            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            // 查看该类时候在Map中有key
            if (parameterMap.containsKey(enclosingElement)) {
                // 有的话把变量继续做添加
                parameterMap.get(enclosingElement).add(element);
            } else {
                // 没有存放key
                List<Element> fields = new ArrayList<>();
                fields.add(element);
                parameterMap.put(enclosingElement, fields);
            }
        } // end 把所有变量和对应类整合成了map

        // 通过Element工具类，获取Parameter类型
        TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE); // 获取activity
        TypeElement parameterType = elementTool.getTypeElement(ProcessorConfig.API_PARAMETER); // 获取ParameterGet接口

        // 传入的参数
        ParameterSpec targetParameter = ParameterSpec.builder(TypeName.OBJECT, ProcessorConfig.INPUT_PARAMETER).build();

        // 遍历map去生成对应文件
        for (Map.Entry<TypeElement, List<Element>> entry : parameterMap.entrySet()) {
            // key == MainActivity
            // value = [name,age]

            // Map集合中的key是类名，如：MainActivity
            TypeElement typeElement = entry.getKey();

            // 如果类名的类型和Activity类型不匹配
            if (!typeTool.isSubtype(typeElement.asType(), activityType.asType())) {
                throw new RuntimeException("@Parameter注解目前仅限用于Activity类之上");
            }

            /**
             * 模板
             * public class Order_MainActivity$$Parameter implements ParameterGet {
             *   @Override
             *   public void getParameter(Object targetParameter) {
             *     Order_MainActivity t = (Order_MainActivity) targetParameter;
             *     t.name = t.getIntent().getStringExtra("name");
             *   }
             * }
             */
            ClassName className = ClassName.get(typeElement);
            // 方法
            // @Override
            // public void getParameter(Object targetParameter)
            MethodSpec.Builder method = MethodSpec.methodBuilder(ProcessorConfig.FUN_PARAMETER)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(targetParameter);
            // 语句1 ： Order_MainActivity t = (Order_MainActivity) targetParameter;
            // 拿到类节点对象，和new xxx() 一个意思，为了能拿到里面的变量
            method.addStatement("$T target = ($T)" + ProcessorConfig.INPUT_PARAMETER, className, className);

            // 方法语句2：t.name = t.getIntent().getStringExtra("name");
            // 遍历注解的属性节点 生成函数体
            for (Element element : entry.getValue()) {
                TypeMirror typeMirror = element.asType();
                // 获取 TypeKind 枚举类型的序列号 == Int,String,Boolean 等
                int type = typeMirror.getKind().ordinal();
                // 获取属性名
                String fieldName = element.getSimpleName().toString();

                // TODO 最终拼接的前缀：
                String finalValue = "target." + fieldName; // == t.name
                String methodContent = finalValue + " = target.getIntent().";  // == t.name = t.getIntent().

                // TypeKind 枚举类型不包含String
                // intent.getIntExtra("age",-1)  ("xx",default value)
                // intent.getBooleanExtra("x",false)
                // intent.getStringExtra("xxx")

                // methodContent = t.name = t.getIntent().
                if (type == TypeKind.INT.ordinal()) {
                    // 现在的 finalValue == Activity里面定义的变量，可以带初始值的
                    methodContent += "getIntExtra($S, " + finalValue + ")";
                } else if (type == TypeKind.BOOLEAN.ordinal()) {
                    methodContent += "getBooleanExtra($S, " + finalValue + ")";
                } else {
                    // String
                    if (typeMirror.toString().equalsIgnoreCase(ProcessorConfig.STRING)) {
                        methodContent += "getStringExtra($S)";
                    }
                }

                // @Parameter(name = "xxxx")
                // String qqq
                // 获取注解的值 == (name = "xxxx")
                String annotationValue = element.getAnnotation(Parameter.class).name();
                // 判断注解的值为空的情况下的处理（注解中有name值就用注解值）
                annotationValue = ProcessorUtils.isEmpty(annotationValue) ? fieldName : annotationValue;

                // 健壮代码
                if (methodContent.endsWith(")")) {
                    // 添加最终拼接方法内容语句
                    method.addStatement(methodContent, annotationValue);
                } else {
                    messager.printMessage(Diagnostic.Kind.ERROR, "目前暂支持String、int、boolean传参");
                }

            }

            // 类
            // public class Order_MainActivity$$Parameter implements ParameterGet {
            String typeName = typeElement.getSimpleName().toString() + "$$Parameter";
            TypeSpec typeSpec = TypeSpec.classBuilder(typeName)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ClassName.get(parameterType))
                    .addMethod(method.build())
                    .build();
            // 包
            JavaFile javaPort = JavaFile.builder(className.packageName(), typeSpec).build();
            try {
                javaPort.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
