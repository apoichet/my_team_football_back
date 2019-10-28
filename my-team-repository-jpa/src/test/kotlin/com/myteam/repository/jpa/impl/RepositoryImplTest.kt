package com.myteam.repository.jpa.impl

import org.junit.jupiter.api.AfterEach
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

    private val driver = "org.h2.Driver"
    private val url = "jdbc:h2:mem:my_team"
    private val user = ""
    private val password = ""
    private val dropDBscriptPath = "src/test/resources/my_team-drop.ddl"
    private val createDBscriptPath = "src/test/resources/my_team-create.ddl"

    private val dropSequence = "DROP TABLE SEQUENCE"

    private val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("my_team_pu_test")
    open val em: EntityManager = emf.createEntityManager()

    @AfterEach
    fun clearDataBase() {
        val connectionProps = Properties()
        connectionProps["user"] = user
        connectionProps["password"] = password
        try {
            getStatement(connectionProps)?.let {
                executeScript(dropDBscriptPath, it)
                it.execute(dropSequence)
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