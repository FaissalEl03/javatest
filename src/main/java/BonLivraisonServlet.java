

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "BonLivraisonServlet", value = "/bonlivraisons")
public class BonLivraisonServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        // Retrieve or create the list of delivery notes
        List<String> bonLivraisons = (List<String>) session.getAttribute("bonLivraisons");
        if (bonLivraisons == null) {
            bonLivraisons = new ArrayList<>();
            session.setAttribute("bonLivraisons", bonLivraisons);
        }

        // Get parameters (id, date, client)
        String idStr = request.getParameter("id");
        String dateStr = request.getParameter("date");
        String client = request.getParameter("client");

        // Add a delivery note if all fields are provided
        if (idStr != null && dateStr != null && client != null &&
            !idStr.isEmpty() && !dateStr.isEmpty() && !client.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(dateStr);
                
                String bonLivraisonStr = String.format(
                    "ID: %d, Date: %s, Client: %s", 
                    id, dateFormat.format(date), client
                );
                bonLivraisons.add(bonLivraisonStr);
            } catch (Exception e) {
                response.getWriter().println("Erreur de format: ID doit être un nombre, Date au format yyyy-MM-dd");
            }
        }

        // Display the list
        response.setContentType("text/plain");
        response.getWriter().println("Liste des bons de livraison :");
        for (String bl : bonLivraisons) {
            response.getWriter().println("- " + bl);
        }
    }
}