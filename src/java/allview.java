/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author merarli
 */
public class allview extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //コネクションとステートメントの宣言
        Connection con = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>すべての投稿一覧</title>");
            out.println("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("<script type=\"text/javascript\" src=\"script.js\"></script>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<a href=\"index.html\" ><h1 class=\"top\">出会いMerarli　ああさああ</h1></a>");
            out.println("<div class=\"div-main\">");
            out.println("<div class=\"div-in\">");
            

//            Class.forNameの記述
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            //データベースへの接続
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/deaiDB", "kadaiyou", "Kadaiyou1!");
            stmt = con.createStatement();
            request.setCharacterEncoding("UTF-8");

            String sql2 = "select * from postlist order by postid desc";
            ps = con.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            
            //投稿フォーム
            
            out.println("<div class=\"post\">");
            
            //ポップアップ

            
            out.println("<h3>新規投稿</h2>");
            out.println("<form action=\"post2\" id=\"" + "post" + "\"method=\"post\">");
            
            out.println("<p class=\"text\">ニックネーム");
            out.println("<input type=\"text\" name=\"username\" value=\"\"></p>");
            out.println("<p class=\"text\">性別");
            out.println("<select name=\"sex\">");
            out.println("<option value=\"男\">男</option>");
            out.println("<option value=\"女\">女</option>");
            out.println("<option value=\"それ以外\">それ以外</option>");
            out.println("</select></p>");
            out.println("<p class=\"text\">年齢(半角数字)");
            out.println("<input type=\"text\" name=\"age\" value=\"\"></p>");
            out.println("<p>アピールポイント</p>");
            out.println("<textarea class=\"reply\" type=\"text\" name=\"appeal\" value=\"\"></textarea>");
            out.println("<p></p>");
            out.println("<input class=\"square_btn\" type=\"submit\" name=\"btn1\" value=\"投稿する♥\">");
            
            out.println("</form>");

            out.println("</div>");
            

            int count = 0;

            //データベースから値を取得して出力
            while (rs.next()) {
                out.println("<div class=\"post\">");
                out.println("<b>" + rs.getString("username") + "</b>");
                int postidTmp = rs.getInt("postid");
                out.println("<span class=\"gray\">投稿ID:" + postidTmp);
                out.println(rs.getString("date") + "<br></span>");
                out.println("<span class=\"gray\">性別:" + rs.getString("sex"));
                out.println("年齢:" + rs.getInt("age") + "</span><br>");
                out.println("<div class=\"box11\">"+rs.getString("appeal") + "</div>");

                
                //ひんしん用SQL
                String sql3 = "select * from replylist where getpostid = " + rs.getInt("postid");
                ps2 = con.prepareStatement(sql3);
                ResultSet rs2 = ps2.executeQuery();
                
                out.println("<p></p>");
                
                out.println("<h3>みんなの返信</h2>");
                while (rs2.next()) {
                    out.println("<div class=\"reply-list\">");
                    out.println("<span class=\"gray\">返信ID:" + rs2.getInt("replyid"));
                    out.println(rs2.getString("date") + "</span><br>");
                    out.println(rs2.getString("appeal") + "<br>");
                    out.println("</div>");
                }
                out.println("<p></p>");
                //フォーム
                out.println("<form action=\"reply\" id=\"" + postidTmp + "\"method=\"post\">");
                out.println("<textarea class=\"reply\" type=\"text\" name=\"appeal\" value=\"\"></textarea>");
                out.println("<p><input type=\"hidden\" name=\"getpostid\" value=\"" + postidTmp + "\"></p>");
                out.println("<p><input class=\"square_btn\" type=\"submit\" name=\"btn1\" value=\"返信する♥\"></p>");
//                out.println("<a href=\"#\" class=\"square_btn\" type=\"submit\" name=\"btn1\">返信する♥</a>");
                out.println("</form>");

                
                out.println("</div>");

            }

            //ResultSetのclose
            rs.close();

            out.println("</div>");
            out.println("<div class=\"div-in-35\">");

            out.println("広告");
            out.println("</div>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            //サーブレット内での例外をアプリケーションのエラーとして表示
            throw new ServletException(e);
        } finally {
            //例外が発生する・しないにかかわらず確実にデータベースから切断
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
