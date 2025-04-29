import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import javax.servlet.http.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloServletTest {
    
    @Test
    public void testDoGet() throws Exception {
        // Create mock objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        // Set up StringWriter to capture servlet output
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        // Create servlet instance and call doGet
        HelloServlet servlet = new HelloServlet();
        servlet.doGet(request, response);
        
        // Verify the response
        writer.flush();
        assertTrue(stringWriter.toString().contains("Hello, World!"));
    }
}