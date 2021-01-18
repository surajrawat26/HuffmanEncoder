package com.Ecj;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

class Node
{
    Character ch;
    Integer freq;
    Node left = null, right = null;

    Node(Character ch, Integer freq) {
        this.ch = ch;
        this.freq = freq;
    }
    public Node(Character ch, Integer freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}
public class HuffmanEncoder extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {
		res.setContentType("text/HTML");
		
		String str = req.getParameter("inputAlphabets");
		PrintWriter out = res.getWriter();
		out.println("<HTML><HEAD>"
				+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1\" crossorigin=\"anonymous\">"
				+"<link href=\"https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,500;0,800;1,400;1,500&display=swap\" rel=\"stylesheet\">");
		
		out.println("<body style=\"background-color:#dff4f3;font-family: 'Montserrat', sans-serif;\">");
		
		if (str == null || str.length() == 0) {
            return;
        }							
        Map<Character, Integer> freq = new HashMap<>();
        for (char c: str.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
            
        }
        
        PriorityQueue<Node> pq;
        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        
        while (pq.size() != 1)
        {
            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }
        Node root = pq.peek();
        Map<Character, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);
        

      
        out.println("<div class='container' style= \'background-color:#fff;height:auto;width:50%;text-align:center; margin-top:50px;padding:2rem; box-shadow: 0px .2px .2px rgba(0, 0, 0, 0.25);\r\n"
        		+ "border-radius: 28px;\'>");
        
        out.println("<div class='container mb-1 ' style='color:#364f6b;'>"
        		+"<div class='row'>"
        		+"<div class='col-sm-6 display-6' style='background-color:#f9f7f7;'>"
        		+"Key" + "</div>"
        		+"<div class='col-sm-6 display-6' style='background-color:#dbe2ef;'>"
                +"Value"+ "</div>"
        		+ "</div>"
        		+ "</div>")	;
        
        for(Map.Entry<Character,String> me : huffmanCode.entrySet()) {
        out.println("<div class='container mb-1'style='color:#364f6b;'>"
        		+"<div class='row'>"
        		+"<div class='col-sm-6' style='background-color:#f9f7f7;'>"
        		+me.getKey() + "</div>"
        		+"<div class='col-sm-6' style='background-color:#dbe2ef;'>"
                +me.getValue() + "</div>"
        		+ "</div>"
        		+ "</div>")	;
        }
        StringBuilder sb = new StringBuilder();
        for (char c: str.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }
        out.println("<p style='color:#364f6b;' class='display-6'>Original String is : </p>"+"<p style='color:#364f6b;word-wrap:break-word;'>"+str+"</p>");
        out.println("<p class='display-6 mt-3 'style='color:#364f6b;display:block;'>Encoded string is : "+"</p>" +"<p style='color:#364f6b;'>"+"<span style='word-wrap:break-word;'>"+ sb+"</span>"+"</p>");                    
        out.println("<button style='margin-top:1rem;background-color:#364f6b;'type='button' onclick=\"window.location.href='Index.html'\" class='btn btn-primary'>Re-Enter text</button>");
        out.println("</div>"+"</head></html>");
       
        }
	public static void encode(Node root, String str,Map<Character, String> huffmanCode)
    {		
        if (root == null) {
            return;
        }
        if (isLeaf(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }
        encode(root.left, str + '0', huffmanCode);
        encode(root.right, str + '1', huffmanCode);
    }
    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }
    }
