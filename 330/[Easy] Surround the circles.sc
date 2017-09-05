import java.text.DecimalFormat

//Find the bounding rectangle of a circle
//as a Vector of the 4 vertice
def findBoundingRectangle(circle: Vector[Double]): Vector[Vector[Double]] ={
  val x = circle(0)
  val y = circle(1)
  val r = circle(2)
  Vector(
    // min/min
    Vector(x-r, y-r),
    // min/max
    Vector(x-r, y+r),
    // max/max
    Vector(x+r, y+r),
    // max/min
    Vector(x+r, y-r)
  )
}

//Rotate the coordinate system of a point from an angle in degrees
def rotateSystem(angleDeg: Double, point: Vector[Double]): Vector[Double] ={
  Vector(
    point(0)*Math.cos(angleDeg) - point(1)*Math.sin(angleDeg),
    point(0)*Math.sin(angleDeg) + point(1)*Math.cos(angleDeg)
  )
}



//Having fun with scala Vectors
val circleInput: Vector[Vector[Double]] = Vector(
  Vector(1,1,2),
  Vector(2,2,0.5),
  Vector(-1,-3,2),
  Vector(5,2,1)
)

val vectorInput: Vector[Double] = Vector(1,1)



//Calculate the rotation angle, be sure that
//it is in degrees
val rotationAngle = Math.atan(vectorInput(1)/vectorInput(0))

//Rotate the coordinate system of the circles' centers
val rotatedCirles = circleInput.foldLeft(Vector(): Vector[Vector[Double]]){
  (acc, circle) => {
    //Be sure not to loose the radius value
    val point = Vector(circle(0), circle(1))
    acc ++ Vector(rotateSystem(rotationAngle, point) ++ Vector(circle(2)))
  }
}

//Accumulate all the bounding rectangle vertice
val verticeAccumulator =
rotatedCirles.foldLeft(Vector(): Vector[Vector[Double]]){
  (acc, circle) =>
    acc ++ findBoundingRectangle(circle)
}

//Switching to Lists os I can use min and max

//Rework the vector to get the list of x
val listX = verticeAccumulator.foldLeft(List(): List[Double]){
  (acc, vertex) => {
     acc.::(vertex.head)
  }
}

//and a list of y
val listY = verticeAccumulator.foldLeft(List(): List[Double]){
  (acc, vertex) => {
    acc.::(vertex.last)
  }
}

//Extract minx, maxx, miny, maxy as formatted Strings
//and build the result points
val minx = listX.min
val maxx = listX.max
val miny = listY.min
val maxy = listY.max
val resultVertice = Vector(
  Vector(minx, miny),
  Vector(minx, maxy),
  Vector(maxx, maxy),
  Vector(maxx, miny)
)

//Rotate back the coordinate system of the resulting vertices
val rotatedVertice = resultVertice.foldLeft(Vector(): Vector[Vector[Double]]){
  (acc, vertex) =>
    acc ++ Vector(rotateSystem(-rotationAngle, vertex))
}

//Build 3 rd decimal result
val df: DecimalFormat = new DecimalFormat("#.000")
rotatedVertice.foldLeft(""){
  (resultString, vertex) =>
    resultString + "(" + df.format(vertex(0)) + "," + df.format(vertex(1)) + ")"
}.replaceAll("\\)\\(", "),(")
