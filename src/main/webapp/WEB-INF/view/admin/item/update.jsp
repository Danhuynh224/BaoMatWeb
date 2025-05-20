<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<jsp:include page="../layout/headerImport.jsp" />

<body class="sb-nav-fixed">
<jsp:include page="../layout/header.jsp" />
<div id="layoutSidenav">
  <jsp:include page="../layout/sidebar.jsp" />

  <div id="layoutSidenav_content">
    <main>
      <div class="container-fluid px-4">
        <h1 class="mt-4">Create Variant</h1>
        <hr />

        <!-- Import header -->
        <!-- <%--<c:import url=""/>--%> -->




        <div class="container mt-5">
          <div class="row">
            <div class="col-md-6 col-12 mx-auto">
              <form:form action="/admin/item/update" method="post" enctype="multipart/form-data" modelAttribute="item" class="row">


                  <div class="mb-3 col-12" style="display: none">

                      <form:input type="text" class="form-control"  path="id"/>
                      <!-- <%--                    ${errorEmail}--%> -->
                  </div>
                  <div class="mb-3 col-12 ">
                      <lable class ="form-label">Product </lable>

                      <form:select path="product.name" class="form-select mt-2">
                          <c:forEach var="p" items="${products}">
                              <option value="${p.name}" ${item.product.name eq p.name ? 'selected' : ''}>
                                      ${p.name}
                              </option>
                          </c:forEach>
                      </form:select>


                  </div>

                  <div class="mb-3 col-12 ">
                      <lable class ="form-label">Color </lable>
                      <form:select path="color.name" class="form-select mt-2" >
                          <c:forEach var="c" items="${colors}">
                              <option value="${c.name}" ${c.name eq item.color.name ? 'selected' : '' }>${c.name}</option>
                          </c:forEach>

                      </form:select>
                  </div>

                  <div class="mb-3 col-12 ">
                      <lable class ="form-label">Size </lable>
                      <form:select path="size.name" class="form-select mt-2" >
                          <c:forEach var="s" items="${sizes}">
                              <option value="${s.name}" ${s.name eq item.size.name ? 'selected' : '' }>${s.name}</option>
                          </c:forEach>

                      </form:select>
                  </div>

                  <c:if test="${ not empty item.image}">
                      <div class="mb-3 col-12 col-md-6" style="display: flex; justify-content: center">
                          <img style="display: inline-block; width: 100px; height: 150px;" src="/images/product/${item.product.name}/${item.image}">
                      </div>
                  </c:if>
                  <br/>

                  <div class="mb-3 col-12 col-md-6" >
                      <label for="imageItem" class="form-label">Image</label>
                      <input class="form-control" type="file" id="imageItem" name="imageItem" accept=".png, .jpg, . jpeg">
                  </div>


                <div class="mb-3 col-12 col-mb-6">

                  <label class="form-label">Quantity</label>
                  <form:input type="text" class="form-control"  path="quantity"/>
                  <!-- <%--                    ${errorEmail}--%> -->
                </div>


                <div class="mb-3 col-12 col-mb-6">

                  <label class="form-label">Price</label>
                  <form:input type="text" class="form-control"  path="price"/>
                  <!-- <%--                    ${errorEmail}--%> -->
                </div>




                <div class = "mb-3 col-12 col-md-6">
                  <button type="submit" class="btn btn-primary">Submit</button>
                  <a href="/admin/item" class ="btn btn-dark">Cancel</a>

                </div>
              </form:form>

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
