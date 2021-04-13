package liyihuan.app.android.androidpractice.kotlin

/**
 * @author created by liyihuanx
 * @date 2021/4/13
 * description: 类的描述
 */
class C2CParser : msgParser{
    override fun parser(uesrbean: UserBean) : UserBean? {
        when(uesrbean.age){
            1 -> {
                uesrbean.username = "li"
                return uesrbean
            }
            2 -> {
                uesrbean.username = "yi"
                return uesrbean
            }

            3 -> {
                uesrbean.username = "huan"
                return uesrbean
            }
            else ->{
                return null
            }
        }
        return null
    }
}