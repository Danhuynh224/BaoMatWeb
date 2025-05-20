
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="../layout/headerImport.jsp" />

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
        <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4" style="margin-left: 10px">Account</h1>
                            <!-- <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item active">Dashboard</li>
                            </ol> -->
                            <div class="container mt-5">
                                <div class="row">
                                    <div class="col-md-12 col-12 mx-auto">

                                        <table class="table table-bordered table-hover">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Name</th>
                                                    <th scope="col">Email</th>
                                                    <th scope="col" style="text-align: center">Action</th>


                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="account" items="${accounts}">

                                                    <tr>
                                                        <th scope="row">${account.user.name}</th>
                                                        <td>${account.email}</td>





                                                        <td style="text-align: center">
                                                            <a href="/admin/user/${account.user.id}"
                                                                class="btn btn-success">View</a>


<%--                                                            <a href="/admin/user/delete/${user.id}"--%>
<%--                                                                class="btn btn-danger">delete</a>--%>

                                                            <!-- <form action = "/admin/user/delete/${user.id}" method = "post" onsubmit="return submitDelete(); ">
                                           <button type="button">  delete</button>
                                        </form> -->

                                                        </td>

                                                    </tr>

                                                </c:forEach>


                                        </table>
                                        <c:if test="${totalPages > 1}">

                                        <nav aria-label="Page navigation example">
                                            <ul class="pagination justify-content-center">
                                                <li class="page-item">
                                                    <a class="${1 eq currentPage ? 'disabled': ''} page-link"
                                                        href="/admin/account?page=${currentPage - 1}"
                                                        aria-label="Previous">
                                                        <span aria-hidden="true">&laquo;</span>
                                                    </a>
                                                </li>
                                                <c:forEach begin="1" end="${totalPages}" varStatus="loop">

                                                    <li class="page-item">
                                                        <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link"
                                                            href="/admin/account?page=${loop.index}">${loop.index}</a>

                                                    </li>
                                                </c:forEach>



                                                <li class="page-item">
                                                    <a class="${currentPage eq totalPages ? 'disabled' : ''}  page-link"
                                                        href="/admin/account?page=${currentPage + 1}" aria-label="Next">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>
                                        </c:if>
                                    </div>

                                </div>

                            </div>

                        </div>
                    </main>
                </div>
  </body>
</html>
