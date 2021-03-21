package liyihuan.app.android.mrouter_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@SupportedAnnotationTypes({ProcessorConfig.MROUTER})

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

    // 仓库一  PATH
    private Map<String, List<RouterBean>> mAllPathMap = new HashMap<>();

    // 仓库二 GROUP
    private Map<String, String> mAllGroupMap = new HashMap<>();

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

        // 打印
//        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>> options <<<<<<<<<" + options);
//        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>> aptPackage <<<<<<<<<" + aptPackage);


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

        // 获取Activity type
        TypeMirror activityMirror = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE).asType();
        // 定义（拿到标准 TYPE） PATH  GROUP
        TypeElement API_PATH = elementTool.getTypeElement(ProcessorConfig.API_PATH);
        TypeElement API_GROUP = elementTool.getTypeElement(ProcessorConfig.API_GROUP);


        // 获取所有被 @ARouter 注解的 元素集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MRouter.class);
        for (Element element : elements) {
            // 注: 被@MRouter注解的类有：LiveActivity
            // 注: 被@MRouter注解的类有：MainActivity
            // 获取简单类名，例如：MainActivity
            String className = element.getSimpleName().toString();
            // 获取使用注解的是什么类 ---> Activity，Fragment或者其他....
            TypeMirror elementMirror = element.asType();
            messager.printMessage(Diagnostic.Kind.NOTE, "被@MRouter注解的类有：" + className);

            // 存放路由的一些信息
            RouterBean routerBean = null;
            // 拿到注解
            MRouter mRouter = element.getAnnotation(MRouter.class);

            // 判断一下使用注解的是否符合规定的类型
            if (typeTool.isSubtype(elementMirror, activityMirror)) {
                // 创建RouterBean对象
                routerBean = new RouterBean.Builder()
                        .addElement(element) // 添加类节点
                        .addType(RouterBean.TypeEnum.ACTIVITY) // 添加类型
                        .addPath(mRouter.path())
                        .addGroup(mRouter.group()) // 使用注解没赋值的话，group == null
                        .build();
            } else {
                throw new RuntimeException("当前@MRouter只能注解Activity [" + elementMirror + "] 不符合");
            }

            // 检查一下Path，顺便给Group赋值
            if (checkPathAndGroup(routerBean)) {
                // path ok -->  保存在HashMap仓库里面
                // HashMap<String,List<routerBean>> {
                //      "app", List<routerBean> ---> MainActivity，Main2Activity 等路由信息
                //      "order", List<routerBean>  ---> OrderActivity, Order2Activity
                //      "mine", List<routerBean>  ---> MineActivity, Mine2Activity
                // }

                // 获取group下的list
                List<RouterBean> routerBeanList = mAllPathMap.get(routerBean.getGroup());
                // 没有list，创建然后添加
                if (routerBeanList == null || routerBeanList.isEmpty()){
                    routerBeanList = new ArrayList<>();
                    routerBeanList.add(routerBean);
                    mAllPathMap.put(routerBean.getGroup(),routerBeanList);
                } else {
                    routerBeanList.add(routerBean);
                }
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "path 不对啊！");
            }
        } // for end

        // TODO 生成PATH文件
        try {
            createPathFile(API_PATH);
            messager.printMessage(Diagnostic.Kind.NOTE, "PATH文件生成成功：");
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "生成path出异常：" + e.getMessage());
        }
        
        // TODO 生成Group文件
        try {
            createGroupFile(API_PATH,API_GROUP);
            messager.printMessage(Diagnostic.Kind.NOTE, "PATH文件生成成功：");
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "生成path出异常：" + e.getMessage());
        }

        return true;
    }

    /**
     * 检查一下Path，Group，Group没赋值的话要从path切出值来
     * @param routerBean
     * @return
     */
    private boolean checkPathAndGroup(RouterBean routerBean) {
        if (routerBean == null) {
            return false;
        }

        String path = routerBean.getPath(); // ---> /app/MainActivity
        String group = routerBean.getGroup();

        if (!path.startsWith("/")){
            messager.printMessage(Diagnostic.Kind.ERROR, "Path：要开头为 / ");
            return false;
        }


        if (group == null || group.length() == 0) {
            group = path.substring(1, path.indexOf("/", 1));
            routerBean.setGroup(group);
        }
        return true;
    }

    /**
     * 给每个Activity生成对应的path类
     * @param API_PATH
     * @throws IOException
     */
    private void createPathFile(TypeElement API_PATH) throws IOException {
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
        // public Map<String, RouterBean> getPathMap()
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder(ProcessorConfig.FUN_PATH)
                .addAnnotation(Override.class) // 给方法上添加注解
                .addModifiers(Modifier.PUBLIC)
                .returns(methodReturn);

        // Map<String, RouterBean> pathMap = new HashMap<>();
        methodSpec.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(RouterBean.class),
                ProcessorConfig.RETURN_PATH,
                ClassName.get(HashMap.class));


        // 第一个循环：Map<String, List<RouterBean>>
        for (Map.Entry<String, List<RouterBean>> entry :mAllPathMap.entrySet()){
            // 第二个循环：
            // entry.getKey() = "app","order"
            // entry.getValue() = List<RouterBean>
            for (RouterBean routerBean : entry.getValue()){
            // 每个path文件,看起来都只有一句话不一样....

            /* pathMap.put("/app/MainActivity",
                    RouterBean.create(RouterBean.TypeEnum.ACTIVITY,
                    MainActivity.class, "/app/MainActivity", "app")) */

                methodSpec.addStatement("$N.put($S, $T.create($T.$L, $T.class, $S, $S))",
                        ProcessorConfig.RETURN_PATH,  // 返回值，常量 pathMap.put(
                        routerBean.getPath(),  // path pathMap.put(path,
                        ClassName.get(RouterBean.class), // pathMap.put(path,RouterBean.create())
                        ClassName.get(RouterBean.TypeEnum.class), // RouterBean.create( RouterBean.TypeEnum
                        routerBean.getTypeEnum(), // ==>"Activity", RouterBean.create( RouterBean.TypeEnum.ACTIVITY,
                        ClassName.get((TypeElement) routerBean.getElement()), // MainActivity.class, // ==>"MainActivity", RouterBean.create( RouterBean.TypeEnum.ACTIVITY,MainActivity.class
                        routerBean.getPath(),  // "/app/MainActivity"
                        routerBean.getGroup()  // "app"
                );
            } // for2 end


            methodSpec.addStatement("return $N", ProcessorConfig.RETURN_PATH);
            MethodSpec build = methodSpec.build();


            // 类: classBuilder(类名)
            //        .addModifiers(方法类型)
            //        .superclass(继承的类)
            //        .addMethod(添加写好的方法)
            // public class ARouter$$Path$$app implements ARouterPath {
            String clazzName = ProcessorConfig.CLASS_PATH + entry.getKey(); // entry.getKey() ---> group
            TypeSpec typeSpec = TypeSpec.classBuilder(clazzName)
                    .addSuperinterface(ClassName.get(API_PATH)) // 实现ARouterLoadPath接口
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(build)
                    .build();
            // 包
            JavaFile javaPort = JavaFile.builder(aptPackage, typeSpec).build();
            javaPort.writeTo(filer);

            mAllGroupMap.put(entry.getKey(),clazzName);

        } // for1 end

    }


    /**
     *
     * @param API_PATH liyihuan.app.android.mrouter_api.MRouterPath
     * @param API_GROUP liyihuan.app.android.mrouter_api.MRouterGroup
     * @throws IOException
     */
    private void createGroupFile(TypeElement API_PATH,TypeElement API_GROUP) throws IOException {
        // Group模板
//    public class ARouter$$Group$$order implements ARouterGroup {
//        @Override
//        public Map<String, Class<? extends ARouterPath>> getGroupMap() {
//            Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
//            groupMap.put("order", ARouter$$Path$$order.class);  // 每个文件也只有这个不一样不而已
//            return groupMap;
//        }
//    }

        //  mAllGroupMap<"group",path文件的名字>
        //  返回值 Map<String, Class<? extends ARouterPath>>  --> path.class
        TypeName returnType = ParameterizedTypeName.get(
                ClassName.get(Map.class), // Map
                ClassName.get(String.class), // String
                // Class<? extends ARouterPath>
                ParameterizedTypeName.get(
                        ClassName.get(Class.class), // get(path.class)
                        WildcardTypeName.subtypeOf(ClassName.get(API_PATH))
                )
        );

//      @Override
//      public Map<String, Class<? extends ARouterPath>> getGroupMap()
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder(ProcessorConfig.FUN_GROUP)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType);

        // 方法语句：Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>()
        methodSpec.addStatement("$T<$T,$T> $N = new $T<>()",
                        ClassName.get(Map.class),
                        ClassName.get(String.class),
                        ParameterizedTypeName.get(ClassName.get(Class.class),
                                WildcardTypeName.subtypeOf(ClassName.get(API_PATH))),
                        ProcessorConfig.RETURN_GROUP,
                        ClassName.get(HashMap.class)
                );

        //  groupMap.put("order", ARouter$$Path$$order.class);
        for (Map.Entry<String, String> entry : mAllGroupMap.entrySet()) {
            // entry.getKey() == group
            // entry.getValue() == path文件的名字

            // groupMap.put("order", ARouter$$Path$$order.class)
            methodSpec.addStatement("$N.put($S,$T.class)",
                    ProcessorConfig.RETURN_GROUP,
                    entry.getKey(),
                    ClassName.get(aptPackage,entry.getValue())
                    );
            // return groupMap;
            methodSpec.addStatement("return $N",ProcessorConfig.RETURN_GROUP);
            MethodSpec method = methodSpec.build();
            // 类：
            // public class ARouter$$Group$$order implements ARouterGroup {
            TypeSpec typeSpec = TypeSpec.classBuilder(ProcessorConfig.CLASS_GROUP + entry.getKey())
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(method)
                    .addSuperinterface(ClassName.get(API_GROUP))
                    .build();

            // 包
            JavaFile javaPort = JavaFile.builder(aptPackage, typeSpec).build();
            javaPort.writeTo(filer);
        }




    }

}
