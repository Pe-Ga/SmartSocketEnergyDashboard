<script>

</script>
    <main>
        <h2>TASMOTA DASHBOARD</h2>
        <div class="dashboard-container">
            <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715606227568&to=1715627827568&panelId=4" title="energy total" class="frame-one"></iframe>
            <iframe src="http://localhost:3000/dashboard/new?orgId=1&from=1715604451245&to=1715626051245&viewPanel=1" title="voltage display" class="frame-two"></iframe>
             <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715605063922&to=1715626663922&panelId=2" title="current" class="frame-three"></iframe>
            <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715605416240&to=1715627016240&panelId=3" title="voltage display" class="frame-four"></iframe>
            <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715605214723&to=1715626814723&panelId=1" title="voltage display" class="frame-five"></iframe>
            <iframe src="http://localhost:3000/d-solo/ddligxgufshs0c/tasmota-voltage-dashboard?orgId=1&from=1715606067921&to=1715627667921&panelId=4" title="energy today" class="frame-six"></iframe>

        </div>
    </main>

<style>

    .dashboard-container {
        display: grid;
        grid-template-columns: repeat(4, 1fr); /* Creates 4 columns of equal width for the first four iframes */
        grid-gap: 10px; /* Space between grid items */
    }

    iframe {
        width: 100%;
        height: 300px;
        border: none;
    }

    .frame-five, .frame-six{
        grid-column: 1 / -1;
    }

</style>
