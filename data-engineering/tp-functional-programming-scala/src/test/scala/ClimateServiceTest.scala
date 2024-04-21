import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert( ClimateService.isClimateRelated("pizza") == false)
    assert( ClimateService.isClimateRelated("I love scala") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC") == true)
    assert(ClimateService.isClimateRelated("I worry a lot about global warming !") == true)
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val thirdRecord = (2005, 1, -10.0)
    val list1 = List(firstRecord, secondRecord, thirdRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2), None)

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }

  test("getMinMax, getMinMaxByYear and calculateDifference") {
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val thirdRecord = (2003, 1, 320.1)
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val co2RecordWithType3 = CO2Record(thirdRecord._1, thirdRecord._2, thirdRecord._3)
    val records = List(Some(co2RecordWithType), Some(co2RecordWithType2), Some(co2RecordWithType3))
    assert(ClimateService.getMinMax(records) == Some((320.1, 375.2)))

    assert(ClimateService.getMinMaxByYear(records, 2003) == Some((320.1, 355.2)))
    assert(ClimateService.getMinMaxByYear(records, 2004) == Some((375.2, 375.2)))
    assert(ClimateService.getMinMaxByYear(records, 2006) == None)

    assert(ClimateService.calculateDifference(records) == Some(55.1))
  }

  //@TODO
  test("filterDecemberData") {
    val records = List(
      Some(CO2Record(2021, 11, 408.52)),
      Some(CO2Record(2021, 12, 409.12)),
      Some(CO2Record(2022, 1, 410.25)),
      Some(CO2Record(2022, 12, 411.87))
    )
    val expected = List(
      CO2Record(2021, 11, 408.52),
      CO2Record(2022, 1, 410.25)
    )
    assert(ClimateService.filterDecemberData(records) === expected)

  }
}
