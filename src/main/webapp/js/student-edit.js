document.addEventListener("DOMContentLoaded", function () {

    const courseSelect = document.getElementById("editCourse");
    const semesterSelect = document.getElementById("editSemester");

    function loadSemesters(courseId, selectedSemesterId = null) {
        console.log("Loading semesters for course ID:", courseId);
        semesterSelect.innerHTML =
            '<option value="">-- Select Semester --</option>';

        if (!COURSE_SEMESTERS[courseId]) {
            return
        }


        COURSE_SEMESTERS[courseId].forEach(sem => {
            const opt = document.createElement("option");
            opt.value = sem.id;
            opt.textContent = sem.name;

            if (selectedSemesterId &&
                String(sem.id) === String(selectedSemesterId)) {
                opt.selected = true;
            }

            semesterSelect.appendChild(opt);
        });
    }

    // Handle clicking Edit button
    document.querySelectorAll(".edit-student-btn").forEach(btn => {
        btn.addEventListener("click", function () {

            // Student fields
            document.getElementById("editStudentId").value =
                this.dataset.id;

            document.getElementById("editUsername").value =
                this.dataset.username;

            document.getElementById("editName").value =
                this.dataset.name;

            // Course + semester
            const courseId = this.dataset.courseId;
            const semesterId = this.dataset.semesterId;

            courseSelect.value = courseId;
            loadSemesters(courseId, semesterId);
        });
    });

    // Change semester list when course changes manually
    courseSelect.addEventListener("change", function () {
        loadSemesters(this.value);
    });

});
