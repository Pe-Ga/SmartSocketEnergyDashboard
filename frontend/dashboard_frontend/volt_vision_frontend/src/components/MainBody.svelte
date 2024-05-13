<script>

</script>
    <main>
        <h2>TASMOTA DASHBOARD</h2>
        <div class="dashboard-container">
                <iframe src="http://localhost:3000/public-dashboards/a3e605db9b5c4b90875a2b4bc90770d7" title="voltage display" class="frame-one"></iframe>
                <iframe src="http://localhost:3000/public-dashboards/a3e605db9b5c4b90875a2b4bc90770d7" title="voltage display" class="frame-two"></iframe>
                <iframe src="http://localhost:3000/public-dashboards/a3e605db9b5c4b90875a2b4bc90770d7" title="voltage display" class="frame-three"></iframe>
                <iframe src="http://localhost:3000/public-dashboards/a3e605db9b5c4b90875a2b4bc90770d7" title="voltage display" class="frame-four"></iframe>
                <iframe src="http://localhost:3000/public-dashboards/a3e605db9b5c4b90875a2b4bc90770d7" title="voltage display" class="frame-five"></iframe>
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

    .frame-five {
        grid-column: 1 / -1;
    }
</style>
