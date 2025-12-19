document.addEventListener("DOMContentLoaded", function () {

    const courseSelect = document.getElementById("editCourse");
    const semesterSelect = document.getElementById("editSemester");
    const subjectSelect = document.getElementById("editSubject");

    function resetSemester() {
        semesterSelect.innerHTML =
            '<option value="">-- Select Semester --</option>';
        resetSubject();
    }

    function resetSubject() {
        subjectSelect.innerHTML =
            '<option value="">-- Select Subject --</option>';
    }

    function loadSemesters(courseId, selectedSemesterId = null) {
        resetSemester();

        const course = COURSE_DATA[courseId];
        if (!course) return;

        Object.entries(course.semesters).forEach(([id, sem]) => {
            const opt = document.createElement("option");
            opt.value = id;
            opt.textContent = sem.name;

            if (selectedSemesterId && String(id) === String(selectedSemesterId)) {
                opt.selected = true;
            }

            semesterSelect.appendChild(opt);
        });
    }

    function loadSubjects(courseId, semesterId, selectedSubjectId = null) {
        resetSubject();

        const semester =
            COURSE_DATA?.[courseId]?.semesters?.[semesterId];
        if (!semester) return;

        semester.subjects.forEach(sub => {
            const opt = document.createElement("option");
            opt.value = sub.id;
            opt.textContent = sub.name;

            if (selectedSubjectId && String(sub.id) === String(selectedSubjectId)) {
                opt.selected = true;
            }

            subjectSelect.appendChild(opt);
        });
    }

    // Edit button click
    document.querySelectorAll(".edit-teacher-btn").forEach(btn => {
        btn.addEventListener("click", function () {

            document.getElementById("editStudentId").value = this.dataset.id;
            document.getElementById("editUsername").value = this.dataset.username;
            document.getElementById("editName").value = this.dataset.name;

            const courseId = this.dataset.courseId;
            const semesterId = this.dataset.semesterId;
            const subjectId = this.dataset.subjectId;

            courseSelect.value = courseId;
            loadSemesters(courseId, semesterId);
            loadSubjects(courseId, semesterId, subjectId);
        });
    });

    courseSelect.addEventListener("change", function () {
        loadSemesters(this.value);
    });

    semesterSelect.addEventListener("change", function () {
        loadSubjects(courseSelect.value, this.value);
    });
});
