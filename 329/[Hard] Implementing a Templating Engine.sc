import play.api.libs.json._
import scala.reflect.runtime._
val cm = universe.runtimeMirror(getClass.getClassLoader)
import scala.tools.reflect.ToolBox

val data: JsValue = Json.parse("""
    {
      "foo": "bar",
      "fizz": "buzz",
      "a": 1,
      "b": [1,2,3]
    }
  """)

val inputTemplate: String = """Hello <%= @data["foo"] %>!"""

val pattern = "<%.*%>".r

val first = pattern.findFirstIn(inputTemplate)

val action = first
  .get
  .replaceAll("<%","")
  .replaceAll("%>","")
  .replaceAll("@","")
  .split('=')(1)
  .trim()

val tb = cm.mkToolBox()

tb.eval(tb.parse("""data"""))



