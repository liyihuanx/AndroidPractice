package liyihuan.app.android.mrouter_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import liyihuan.app.android.mrouter_annotation.MRouter;
import liyihuan.app.android.mrouter_annotation.RouterBean;

/**
 * @ClassName: MRouterProcessor
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/1/21 20:31
 */
// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)

// 允许/支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes({ProcessorConfig.MROUTER_PACKAGE})

// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)

// 注解处理器接收的参数
@SupportedOptions({ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE})
public class MRouterProcessor extends AbstractProcessor {

    // 操作Element的工具类（类，函数，属性，其实都是Element）
    private Elements elementTool;

    // type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Types typeTool;

    // Message用来打印 日志相关信息
    private Messager messager;

    // 文件生成器， 类 资源 等，就是最终要生成的文件 是需要Filer来完成的
    private Filer filer;

    // （模块传递过来的）模块名  app，personal
    private String options;
    // （模块传递过来的） 包名
    private String aptPackage;

    /**
     * 初始化
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();

        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>> options <<<<<<<<<" + options);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>> aptPackage <<<<<<<<<" + aptPackage);


    }

    /**
     * 处理注解
     * @param set
     * @param roundEnvironment
     * @return true 表示后续处理器不会再处理（已经处理完成）
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()){
            messager.printMessage(Diagnostic.Kind.NOTE, "没有使用过MRouter注解");
            return false;
        }

        // Activity type
        TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
        TypeMirror activityMirror = activityType.asType();

        messager.printMessage(Diagnostic.Kind.NOTE, "activityType：" + activityType);
        messager.printMessage(Diagnostic.Kind.NOTE, "activityMirror：" + activityMirror);


        // 获取所有被 @ARouter 注解的 元素集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MRouter.class);
        for (Element element : elements) {

            // 获取简单类名，例如：MainActivity
            String className = element.getSimpleName().toString();
            // 打印出 就证明APT没有问题
            messager.printMessage(Diagnostic.Kind.NOTE, "被@MRouter注解的类有：" + className);

            // 拿到注解
            MRouter mRouter = element.getAnnotation(MRouter.class);

            // Path模板
            /*public class ARouter$$Path$$app implements ARouterPath {
                @Override
                public Map<String, RouterBean> getPathMap() {
                    Map<String, RouterBean> pathMap = new HashMap<>();
                    pathMap.put("/app/MainActivity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY, MainActivity.class, "/app/MainActivity", "app"));
                    return pathMap;
                }
            }*/

            // 方法： methodBuilder(方法名)
            //          .addModifiers(方法类型)
            //          .returns(返回类型)
            //          .addParameter(参数)
            //          .addStatement(方法语句)
            //          .build();

            // Map<String, RouterBean>  返回值
            TypeName methodReturn = ParameterizedTypeName.get(
                    ClassName.get(Map.class), // Map
                    ClassName.get(String.class), // Map<String,
                    ClassName.get(RouterBean.class) // Map<String, RouterBean>
            );

            MethodSpec.Builder methodSpec = MethodSpec.methodBuilder("getPathMap")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(methodReturn);

            // Map<String, RouterBean> pathMap = new HashMap<>();
            methodSpec.addStatement("$T<$T, $T> $S = new $T<>()",
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ClassName.get(RouterBean.class),
                    "pathMap",
                    ClassName.get(HashMap.class));

            /* pathMap.put("/app/MainActivity",
                    RouterBean.create(RouterBean.TypeEnum.ACTIVITY,
                    MainActivity.class, "/app/MainActivity", "app")) */



            // 类: classBuilder(类名)
            //        .addModifiers(方法类型)
            //        .superclass(继承的类)
            //        .addMethod(添加写好的方法)
            // public class ARouter$$Path$$app implements ARouterPath {
//            TypeSpec typeSpec = TypeSpec.classBuilder("ARouter$$Path$$app")
//                    .addModifiers(Modifier.PUBLIC)
//                    .superclass()
//                    .build();
            // 包


        }

        return true;
    }
}
