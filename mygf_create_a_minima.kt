fun main() {
    val script = """
        // Simple script example
        var x = 5
        print("Hello, World!")
        if x > 10 then
            print("x is greater than 10")
        else
            print("x is less than or equal to 10")
        end
    """.trimIndent()

    val parser = MinimaScriptParser()
    val scriptTree = parser.parseScript(script)
    val executor = MinimaScriptExecutor()
    executor.executeScript(scriptTree)
}

class MinimaScriptParser {
    fun parseScript(script: String): ScriptTree {
        // Tokenize the script
        val tokens = script.split("\\s+".toRegex())
        // Parse tokens into an Abstract Syntax Tree (AST)
        val scriptTree = ScriptTree()
        var currentNode: ScriptNode? = scriptTree
        tokens.forEach { token ->
            when (token) {
                "var" -> currentNode = scriptTree.addDeclaration(token)
                "print" -> currentNode = scriptTree.addPrintStatement(token)
                "if" -> currentNode = scriptTree.addIfStatement(token)
                "then" -> currentNode = scriptTree.addThenClause(token)
                "else" -> currentNode = scriptTree.addElseClause(token)
                "end" -> currentNode = scriptTree.addEndClause(token)
                else -> scriptTree.addValue(currentNode!!, token)
            }
        }
        return scriptTree
    }
}

data class ScriptTree(val nodes: MutableList<ScriptNode> = mutableListOf()) {
    fun addDeclaration(token: String): ScriptDeclaration {
        val declaration = ScriptDeclaration(token)
        nodes.add(declaration)
        return declaration
    }

    fun addPrintStatement(token: String): ScriptPrintStatement {
        val printStatement = ScriptPrintStatement(token)
        nodes.add(printStatement)
        return printStatement
    }

    fun addIfStatement(token: String): ScriptIfStatement {
        val ifStatement = ScriptIfStatement(token)
        nodes.add(ifStatement)
        return ifStatement
    }

    fun addThenClause(token: String): ScriptThenClause {
        val thenClause = ScriptThenClause(token)
        nodes.add(thenClause)
        return thenClause
    }

    fun addElseClause(token: String): ScriptElseClause {
        val elseClause = ScriptElseClause(token)
        nodes.add(elseClause)
        return elseClause
    }

    fun addEndClause(token: String): ScriptEndClause {
        val endClause = ScriptEndClause(token)
        nodes.add(endClause)
        return endClause
    }
}

sealed class ScriptNode
class ScriptDeclaration(val token: String) : ScriptNode()
class ScriptPrintStatement(val token: String) : ScriptNode()
class ScriptIfStatement(val token: String) : ScriptNode()
class ScriptThenClause(val token: String) : ScriptNode()
class ScriptElseClause(val token: String) : ScriptNode()
class ScriptEndClause(val token: String) : ScriptNode()

class MinimaScriptExecutor {
    fun executeScript(scriptTree: ScriptTree) {
        scriptTree.nodes.forEach { node ->
            when (node) {
                is ScriptPrintStatement -> println("print: ${node.token}")
                is ScriptIfStatement -> println("if: ${node.token}")
                is ScriptThenClause -> println("then: ${node.token}")
                is ScriptElseClause -> println("else: ${node.token}")
                is ScriptEndClause -> println("end: ${node.token}")
            }
        }
    }
}