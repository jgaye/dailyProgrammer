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


//Having fun with scala Vectors
val circleInput: Vector[Vector[Double]] = Vector(
  Vector(1,1,2),
  Vector(2,2,0.5),
  Vector(-1,-3,2),
  Vector(5,2,1)
)

//Accumulate all the bounding rectangle vertice
val verticeAccumulator =
circleInput.foldLeft(Vector(): Vector[Vector[Double]]){
  (acc, circle) =>
    acc ++ findBoundingRectangle(circle)
}

//Swithing to Lists os I can use min and max

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
val df: DecimalFormat = new DecimalFormat("#.000");
val minx = df.format(listX.min)
val maxx = df.format(listX.max)
val miny = df.format(listY.min)
val maxy = df.format(listY.max)

//Print result
val result = s"($minx,$miny), " +
  s"($minx,$maxy), " +
  s"($maxx,$maxy), " +
  s"($maxx,$miny)"
println(result)
