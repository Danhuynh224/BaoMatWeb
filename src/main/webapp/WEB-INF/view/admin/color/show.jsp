
<%@ taglib prefix="c" uri="jakarta.tags.core" %> <%@page contentType="text/html"
                                                         pageEncoding="UTF-8" %>

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
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-12 col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Table color</h3>
                                <a href="/admin/color/create" class="btn btn-primary">Create color</a>
                            </div>
                            <table class="table table-bordered table-hover mt-3">
                                <thead>
                                <tr style="text-align: center; vertical-align: middle;">
                                    <th scope="col">Name</th>
                                    <th scope="col">Description</th>
                                    <th scope="col">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="color" items="${colors}">
                                    <tr style="text-align: center; vertical-align: middle;">
                                        <th scope="row">${color.name}</th>
                                        <td>${color.description}</td>

                                        <td style="text-align: center">
                                            <a href="/admin/color/update/${color.id}" class="btn btn-warning">Update</a>
                                            <c:if test="${color.productVariants.size() <= 0}">
                                            <a href="/admin/color/delete/${color.id}" class="btn btn-danger">Delete</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
<c:if test="${totalPages > 1}">


                            <nav aria-label="Page navigation example">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item">
                                        <a class="${1 eq currentPage ? 'disabled' : ''} page-link"
                                           href="/admin/color?page=${currentPage - 1}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                        <li class="page-item">
                                            <a class="${(loop.index) eq currentPage ? 'active' : ''} page-link"
                                               href="/admin/color?page=${loop.index}">${loop.index}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item">
                                        <a class="${currentPage eq totalPages ? 'disabled' : ''} page-link"
                                           href="/admin/color?page=${currentPage + 1}" aria-label="Next">
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
    <jsp:include page="../layout/footer.jsp" />
</div>
</body>
</html>
