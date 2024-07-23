package one.devos.yiski.common.database

import io.github.oshai.kotlinlogging.KotlinLogging
import one.devos.yiski.common.data.YiskiBotConfig
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.reflections.Reflections
import org.reflections.scanners.Scanners


class DatabaseManager(credentials: YiskiBotConfig.PostgresConfig) {
    private val logger = KotlinLogging.logger { }

    /**
     * The database connection instance.
     * @returns Database
     */
    var database: Database

    init {
        try {
            database = Database.connect(
                "jdbc:postgresql://${credentials.host}:${credentials.port}/${credentials.db}",
                "org.postgresql.Driver",
                credentials.username,
                credentials.password,
                databaseConfig = DatabaseConfig {
                    useNestedTransactions = true
                }
            )

            transaction {
                addLogger(Slf4jSqlDebugLogger)

                logger.info { "Connected to database" }
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to connect to database" }
            throw e
        }
    }

    /**
     * Checks if the database is still connected.
     * @returns Boolean
     */
    fun isConnected(): Boolean = transaction {
        try {
            !connection.isClosed
        } catch (e: Exception) {
            false
        }
    }

    fun registerTables(tablesPackage: String) {
        val reflections: Reflections = Reflections(tablesPackage, Scanners.TypesAnnotated, Scanners.SubTypes)

        val tables = reflections.getTypesAnnotatedWith(DatabaseEntry::class.java)
            .map { it.kotlin }
            .map { it.objectInstance as Table }
            .toTypedArray()

        logger.info { "Found ${tables.size} tables" }
        logger.debug { "Tables: ${tables.joinToString(", ") { "${it.tableName}<${it.javaClass.name}>" }}" }

        transaction {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }
    }
}
