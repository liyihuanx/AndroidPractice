package liyihuan.app.android.androidpractice.kotlin

/**
 * @author created by liyihuanx
 * @date 2021/4/13
 * description: 类的描述
 */
class C2CParser2 : msgParser{
    override fun parser(uesrbean: UserBean) : UserBean? {
        when(uesrbean.age){
            4 -> {
                uesrbean.username = "chen"
                return uesrbean
            }
            5 -> {
                uesrbean.username = "ya"
                return uesrbean
            }

            6 -> {
                uesrbean.username = "lun"
                return uesrbean
            }
            else ->{
                return null
            }
        }
        return null
    }
}