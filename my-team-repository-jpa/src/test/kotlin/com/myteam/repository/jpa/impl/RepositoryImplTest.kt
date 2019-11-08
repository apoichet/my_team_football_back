package com.myteam.repository.jpa.impl

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.BufferedReader
import java.io.FileReader
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

open class RepositoryImplTest {

    private val dataSourceName = "my_team_pu_test"
    private val emf: EntityManagerFactory = Persistence.createEntityManagerFactory(dataSourceName)
    open val em: EntityManager = emf.createEntityManager()

    private val driver = emf.properties["javax.persistence.jdbc.driver"] as String
    private val url = emf.properties["javax.persistence.jdbc.url"] as String
    private val user = emf.properties["javax.persistence.jdbc.user"]?.toString() ?: ""
    private val password = emf.properties["javax.persistence.jdbc.password"]?.toString() ?: ""
    private val dropDBscriptPath = emf.properties["javax.persistence.schema-generation.scripts.drop-target"] as String
    private val createDBscriptPath = emf.properties["javax.persistence.schema-generation.scripts.create-target"] as String

    @AfterEach
    fun clearDataBase() {
        val connectionProps = Properties()
        connectionProps["user"] = user
        connectionProps["password"] = password
        try {
            getStatement(connectionProps)?.let {
                executeScript(dropDBscriptPath, it)
                it.execute("DROP TABLE SEQUENCE")
                executeScript(createDBscriptPath, it)
            }
        } catch (ex: SQLException) {
            // handle any errors
            ex.printStackTrace()
        } catch (ex: Exception) {
            // handle any errors
            ex.printStackTrace()
        }
    }

    private fun getStatement(connectionProps: Properties): Statement? {
        Class.forName(driver).newInstance()
        val conn = DriverManager.getConnection(url, connectionProps)
        val stmt = conn!!.createStatement()
        return stmt
    }

    private fun executeScript(path: String, statement: Statement) {
        val reader = BufferedReader(FileReader(path))
        reader.lines().forEach {
            statement.execute(it)
        }
    }



}