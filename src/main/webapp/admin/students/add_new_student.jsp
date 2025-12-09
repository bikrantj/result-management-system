<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal fade" id="addStudentModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title fw-bold">Add New Student</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <form action="${pageContext.request.contextPath}/add-student" method="post">
                <div class="modal-body">

                    <div class="mb-3">
                        <label class="form-label">Full Name</label>
                        <input type="text" name="fullName" class="form-control" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Username (Login)</label>
                        <input type="text" name="username" class="form-control" required>
                    </div>

                    <!-- Default password is handled in Servlet: 12345678 -->

                    <div class="mb-3">
                        <label class="form-label">Course</label>
                        <select name="courseId" class="form-select" required>
                            <option value="">Select Course</option>
                            <option value="1">BCA</option>
                            <option value="2">BIT</option>
                            <option value="3">BE</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Semester</label>
                        <select name="semesterId" class="form-select" required>
                            <option value="">Select Semester</option>
                            <option>I</option>
                            <option>II</option>
                            <option>III</option>
                            <option>IV</option>
                            <option>V</option>
                            <option>VI</option>
                            <option>VII</option>
                            <option>VIII</option>
                        </select>
                    </div>

                </div>

                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button class="btn btn-success" type="submit">Add Student</button>
                </div>
            </form>

        </div>
    </div>
</div>
