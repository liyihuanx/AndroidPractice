package liyihuan.app.android.androidpractice.http

/**
 * @ClassName: RequestManager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/4/21 21:53
 */
object RepositoryManager {

    private val repoMap by lazy {
        LinkedHashMap<Class<*>, BaseRepository<*>>()
    }

    fun <T> addRepo(repo: BaseRepository<T>) {
        repoMap[repo.javaClass] = repo
    }

    /**
     * 父类.class.isAssignableFrom(子类.class)
     *
     *  子类实例 instanceof 父类类型
     */
    fun <T> getRepo(childRepo: Class<T>): T {
        if (BaseRepository::class.java.isAssignableFrom(childRepo)) {
            //[TestRepository.class, TestRepository()对象]
            var newRepo = repoMap[childRepo]
            if (newRepo == null) {
                try {
                    // TestRepository
                    newRepo = childRepo.newInstance() as BaseRepository<*>
                    addRepo(newRepo)
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }

            return newRepo as T
        }
        throw IllegalArgumentException("the class must extend BaseReposity")
    }


    fun clearRepo() {
        repoMap.clear()
    }
}