<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="addCourseModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title fw-bold">Add New Course</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <form action="${pageContext.request.contextPath}/add-course" method="post">

                <div class="modal-body">

                    <div class="mb-3">
                        <label class="form-label" for="courseName">Course Name</label>
                        <input type="text" name="courseName" id="courseName" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="courseCode">Course Code</label>
                        <input type="text" name="courseCode" id="courseCode" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="semesterCount">Number of Semesters</label>
                        <input type="number" name="semesterCount" id="semesterCount" class="form-control" min="1"
                               max="12" value="8"
                               required>
                    </div>

                </div>

                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button class="btn btn-success" type="submit">Add Course</button>
                </div>

            </form>

        </div>
    </div>
</div>
