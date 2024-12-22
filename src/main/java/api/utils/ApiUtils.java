package api.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils {

    // Generate token
    public static String getToken() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"username\":\"admin\", \"password\":\"password123\"}")
                .post("https://restful-booker.herokuapp.com/auth");

        if (response.getStatusCode() == 200) {
            return response.jsonPath().getString("token");
        } else {
            throw new RuntimeException("Failed to generate token! Status Code: " + response.getStatusCode());
        }
    }

    // Get all booking IDs
    public static Response getBookingIds() {
        return RestAssured.given()
                .get("https://restful-booker.herokuapp.com/booking");
    }

    // Get details of a specific booking
    public static Response getBooking(int bookingId) {
        return RestAssured.given()
                .get("https://restful-booker.herokuapp.com/booking/" + bookingId);
    }

    // Create a new booking
    public static Response createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        String requestBody = String.format("{\n" +
                "  \"firstname\": \"%s\",\n" +
                "  \"lastname\": \"%s\",\n" +
                "  \"totalprice\": %d,\n" +
                "  \"depositpaid\": %b,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"%s\",\n" +
                "    \"checkout\": \"%s\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"%s\"\n" +
                "}", firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);

        return RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("https://restful-booker.herokuapp.com/booking");
    }

    // Update an existing booking
    public static Response updateBooking(int bookingId, String token, String firstname, String lastname, int totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        String requestBody = String.format("{\n" +
                "  \"firstname\": \"%s\",\n" +
                "  \"lastname\": \"%s\",\n" +
                "  \"totalprice\": %d,\n" +
                "  \"depositpaid\": %b,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"%s\",\n" +
                "    \"checkout\": \"%s\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"%s\"\n" +
                "}", firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);

        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .body(requestBody)
                .put("https://restful-booker.herokuapp.com/booking/" + bookingId);
    }

    // Delet existing booking
    public static Response deleteBooking(int bookingId, String token) {
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .delete("https://restful-booker.herokuapp.com/booking/" + bookingId);
    }
}
