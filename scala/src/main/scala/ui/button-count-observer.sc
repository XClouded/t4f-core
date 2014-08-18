object W2 {
  val bco = new ui.ButtonCountObserver            //> bco  : ui.ButtonCountObserver = ui.ButtonCountObserver@75a1cd57
  val oldCount = bco.count                        //> oldCount  : Int = 0
  bco.count = 5
  val newCount = bco.count                        //> newCount  : Int = 5
  println(newCount + " == 5 and " + oldCount + " == 0?")
                                                  //> 5 == 5 and 0 == 0?
}