fun main() {
    val textToFormat = """
        import java.util.Scanner;public class Test
        {private String TEST = "test";public static void main(String args[]) {
        int row, i, j, space = 1;System.out.print("Enter the number of rows you want to print: ");
        Scanner sc = new Scanner(System.in);row = sc.nextInt();
        space = row - 1;for (j = 1; j <= row; j++)
        {for (i = 1; i <= space; i++){System.out.print(" ");}space--;for (i = 1; i <= 2 * j - 1; i++) {
        System.out.print("*");}
        System.out.println("");
        }
        space = 1;for (j = 1; j <= row - 1; j++)
        {for (i = 1; i <= space; i++) {System.out.print(" ");}space++;for (i = 1; i <= 2 * (row - j) - 1;
        i++) {System.out.print("*");
        }System.out.println("");}}
        }
    """.trimIndent()


    val formattedCode = formatJavaCode(textToFormat)
    println(formattedCode)
}

fun formatJavaCode(code: String): String {

    val lines = code.split("\n")
    val formatted = StringBuilder()
    var indentLevel = 0
    var insideForLoop = false

    fun appendLine(line: String) {
        formatted.append("    ".repeat(indentLevel)).append(line.trim()).append("\n")
    }

    for (line in lines) {
        var modifiedLine = line.trim()

        if (modifiedLine.startsWith("for (")) {
            insideForLoop = true
        }

        val tokens = modifiedLine.split(";", "{", "}", ":")

        for (token in tokens) {
            val trimmed = token.trim()
            if (trimmed.isNotEmpty()) {
                if (insideForLoop && trimmed.endsWith(")")) {
                    insideForLoop = false
                    appendLine(trimmed + ";")
                } else {
                    if (trimmed.endsWith(";")) {
                        appendLine(trimmed)
                        if (!insideForLoop) formatted.append("\n")
                    } else if (trimmed.endsWith(":")) {
                        appendLine(trimmed)
                        formatted.append("\n")
                    } else if (trimmed.endsWith("{")) {
                        appendLine(trimmed)
                        formatted.append("\n")
                        indentLevel++
                    } else if (trimmed == "}") {
                        indentLevel--
                        formatted.append("    ".repeat(indentLevel)).append("}\n")
                    } else {
                        appendLine(trimmed)
                    }

                }

            }
        }
    }


    return formatted.toString()
}
