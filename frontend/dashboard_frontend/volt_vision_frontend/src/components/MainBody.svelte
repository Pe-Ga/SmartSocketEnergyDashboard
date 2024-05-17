<script>

</script>
    <main>
        <h1>Dashboard Statistics</h1>
        <div class="dashboard-container">
            <iframe src="http://localhost:3000/dashboard/snapshot/mOTl0SUxhNAL8WJZ7wzvNUAAafL7c4FC" title="energy total" class="frame-one"></iframe>
            <iframe src="http://localhost:3000/dashboard/snapshot/IiXngmDxlAGXVOdpQnuhgEOJDFmZKye5" title="voltage display" class="frame-two"></iframe>
             <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715605063922&to=1715626663922&panelId=2" title="current" class="frame-three"></iframe>
            <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715605416240&to=1715627016240&panelId=3" title="voltage display" class="frame-four"></iframe>
            <h2>Voltage</h2>
            <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715605214723&to=1715626814723&panelId=1" title="voltage display" class="frame-five"></iframe>
            <h2>Total Energy Usage </h2>
            <iframe src="http://localhost:3000/dashboard/snapshot/3OcUexoHf1rsa09kKQ5DKO5SV4OADycj" title="energy today" class="frame-six"></iframe>

        </div>
    </main>

<style>

    .dashboard-container {
        display: grid;
        grid-template-columns: repeat(4, 1fr); /* Creates 4 columns of equal width for the first four iframes */
        grid-gap: 0.5em; /* Space between grid items */
    }

    iframe {
        width: 100%;
        height: 400px;
        border: none;
    }

    .frame-five, .frame-six{
        grid-column: 1 / -1;
    }

</style>
