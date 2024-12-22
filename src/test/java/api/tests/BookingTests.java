package api.tests;

import api.utils.ApiUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTests {

    @Test
    public void testCreateToken() {
        String token = ApiUtils.getToken();
        System.out.println("Token alındı: " + token);
        assertEquals(token.length() > 0, true, "Token alınamadı.");
    }

    @Test
    public void testGetBookingIds() {
        Response response = ApiUtils.getBookingIds();
        assertEquals(200, response.getStatusCode(), "Booking ID'leri alınamadı.");
        System.out.println("Booking ID's: " + response.getBody().asString());
    }

    @Test
    public void testGetBooking() {
        Response createResponse = ApiUtils.createBooking(
                "John", "Doe", 150, true, "2023-01-01", "2023-01-10", "Breakfast"
        );
        int bookingId = createResponse.jsonPath().getInt("bookingid");

        Response response = ApiUtils.getBooking(bookingId);
        assertEquals(200, response.getStatusCode(), "Booking alınamadı.");
        System.out.println("Booking Details: " + response.getBody().asString());
    }

    @Test
    public void testCreateBooking() {
        Response response = ApiUtils.createBooking(
                "Jane", "Smith", 200, true, "2023-02-01", "2023-02-15", "Lunch"
        );
        assertEquals(200, response.getStatusCode(), "Booking oluşturulamadı.");
        System.out.println("Created Booking: " + response.getBody().asString());
    }

    @Test
    public void testUpdateBooking() {
        String token = ApiUtils.getToken();
        Response createResponse = ApiUtils.createBooking(
                "Alice", "Johnson", 300, true, "2023-03-01", "2023-03-10", "Dinner"
        );
        int bookingId = createResponse.jsonPath().getInt("bookingid");

        Response updateResponse = ApiUtils.updateBooking(
                bookingId, token, "Alice", "Brown", 350, false, "2023-03-05", "2023-03-15", "No Needs"
        );
        assertEquals(200, updateResponse.getStatusCode(), "Booking güncellenemedi.");
        System.out.println("Updated Booking: " + updateResponse.getBody().asString());
    }

    @Test
    public void testDeleteBooking() {
        String token = ApiUtils.getToken();
        Response createResponse = ApiUtils.createBooking(
                "Mark", "Spencer", 400, true, "2023-04-01", "2023-04-10", "Snacks"
        );
        int bookingId = createResponse.jsonPath().getInt("bookingid");

        Response deleteResponse = ApiUtils.deleteBooking(bookingId, token);
        assertEquals(201, deleteResponse.getStatusCode(), "Booking silinemedi.");
        System.out.println("Deleted  Booking ID: " + bookingId);
    }
}
