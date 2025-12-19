<form method="POST" action="${pageContext.request.contextPath}/logout">


    <button
            class="group relative inline-flex items-center justify-center overflow-hidden rounded-xl
                          bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-3 text-white font-semibold
                          shadow-lg transition-all duration-300 hover:shadow-xl hover:-translate-y-0.5
                          hover:from-blue-700 hover:to-indigo-700 active:scale-95">

        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
             class="lucide lucide-log-out-icon lucide-log-out">
            <path d="m16 17 5-5-5-5"></path>
            <path d="M21 12H9"></path>
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
        </svg>
        Logout
    </button>
</form>
