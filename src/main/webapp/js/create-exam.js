document.addEventListener("DOMContentLoaded", () => {

    const courseSelect = document.getElementById("examCourse");
    const semesterSelect = document.getElementById("examSemester");

    if (!courseSelect || !semesterSelect) return;

    courseSelect.addEventListener("change", () => {

        const courseId = courseSelect.value;
        semesterSelect.innerHTML =
            '<option value="">-- Select Semester --</option>';

        if (!courseId || !COURSE_SEMESTERS[courseId]) return;

        COURSE_SEMESTERS[courseId].forEach(sem => {
            const opt = document.createElement("option");
            opt.value = sem.id;
            opt.textContent = sem.name;
            semesterSelect.appendChild(opt);
        });
    });

});
