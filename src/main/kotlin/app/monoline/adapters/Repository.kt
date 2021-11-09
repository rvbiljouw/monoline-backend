package app.monoline.adapters

interface Repository<T, ID> {

    fun findById(id: ID): T?

    fun findAll(): Iterable<T>

    fun save(entity: T): Boolean

}
