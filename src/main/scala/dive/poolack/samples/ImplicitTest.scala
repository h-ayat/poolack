package dive.poolack.samples

object ImplicitDefs {

  implicit class TimeHelper(input: Int) {

    def seconds: Long = input * 1000L 
    def minutes: Long = seconds * 60
    def hours: Long = minutes * 60
    def days: Long = hours * 24
  }



}


object Tester {



  import ImplicitDefs.TimeHelper


  val o = 24.hours + 12.minutes + 10.seconds
}
