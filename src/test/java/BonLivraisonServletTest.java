

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class BonLivraisonServletTest {

    @Test
    public void testAddBonLivraison() throws Exception {
        // 1. Mock HttpServletRequest, HttpServletResponse, and HttpSession
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // 2. Simulate session storage (like your friend’s "panier" test)
        List<String> bonLivraisons = new ArrayList<>();
        when(session.getAttribute("bonLivraisons")).thenReturn(bonLivraisons);
        when(request.getSession()).thenReturn(session);

        // 3. Mock request parameters (id, date, client)
        when(request.getParameter("id")).thenReturn("100");
        when(request.getParameter("date")).thenReturn("2023-12-15");
        when(request.getParameter("client")).thenReturn("Client ABC");

        // 4. Capture the servlet’s response
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // 5. Execute the servlet’s doGet method
        BonLivraisonServlet servlet = new BonLivraisonServlet();
        servlet.doGet(request, response);

        // 6. Verify the output
        writer.flush();
        String result = stringWriter.toString();
        System.out.println("Servlet Output:\n" + result);

        // Assertions
        assertTrue(result.contains("ID: 100, Date: 2023-12-15, Client: Client ABC")); // Check response
        assertTrue(bonLivraisons.contains("ID: 100, Date: 2023-12-15, Client: Client ABC")); // Check session
    }

    @Test
    public void testMissingParameters() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));

        StringWriter stringWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        BonLivraisonServlet servlet = new BonLivraisonServlet();
        servlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("Liste des bons de livraison"));
        assertFalse(stringWriter.toString().contains("ID:")); // No entry added
    }
}