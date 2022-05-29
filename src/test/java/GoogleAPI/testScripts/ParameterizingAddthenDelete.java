package GoogleAPI.testScripts;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import restAssuredFiles.Payload_Books;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;




 public class ParameterizingAddthenDelete {
	
	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle)
	
	{
		//Adding the Book & getting BookID
	  RestAssured.baseURI= "http://216.10.245.166";
	  String addResource = "Library/Addbook.php";
	  String addresponse = given().header("Content-Type","application/json").body(Payload_Books.addBook(isbn,aisle)).
	  when().post(addResource).
	  then().assertThat().body("Msg", equalTo("successfully added")).extract().body().asString();
	  
	 System.out.println("addBook respose message is => " + addresponse);
	 JsonPath js = new JsonPath(addresponse);
	 String resposnseID = js.getString("ID");	  
	 System.out.println("Book id is => "+resposnseID);
	 
	 
	// Deleting the Book Which is added recently
		 String deleteResource ="/Library/DeleteBook.php";
		 String deleteResponce = given().header("Content-Type","application/json").body(Payload_Books.deleteBook(resposnseID))
		 .when().delete(deleteResource)
		 .then().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted")).extract().response().asString();
		 
		 JsonPath js2 = new JsonPath(deleteResponce);
		 String DeleteMessage =js2.getString("msg");
		 
		 System.out.println("deleteBook respose message is => " +deleteResponce);
		 System.out.println(DeleteMessage);
	 
	}
	
	 
	 @DataProvider(name="BooksData")
	 public Object [][] getData()
	 {
		Object[][] ob= {{"pahadiAmigo","1111"},{"pahadiAmigo","2222"},{"pahadiAmigo","3333"},{"pahadiAmigo","4444"}};
		return ob;
	 }
	
	 

}
