<script>
    import {onMount} from "svelte";
    import DeviceForm from "./DeviceForm.svelte";
    import DeviceCard from "./DeviceCard.svelte";

    let devices = [];
    let showForm = false;
    let imageUrl = '/images/smart-plug.png'; // Updated image path
    let title = "Smart Plug";

    onMount(async () => {
        const response = await fetch('http://localhost:8181/devices');
        devices = await response.json();
    });

    function registerDevice() {
        showForm = true;
    }

    async function handleSubmission(event) {
        const {name, ipAddress} = event.detail;
        const response = await fetch('http://localhost:8181/devices', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({name, ipAddress}),
        });

        if (response.ok) {
            const newDevice = await response.json();
            devices = [...devices, newDevice];
            showForm = false;
        }
    }
</script>

<aside class="device-sidebar">
    <h3>Registered Devices</h3>
    <div class="device-section">
        {#each devices as device}
            <DeviceCard device={device} imageUrl={imageUrl} title={title} /> <!-- Use DeviceCard here -->
        {/each}
    </div>
    <button type="button" id="register-device-button" on:click={registerDevice}>Add New Device</button>
    {#if showForm}
        <DeviceForm on:submit={handleSubmission} on:close={() => showForm = false}/>
    {/if}
</aside>

<style>

    .device-sidebar {
        /*
        flex: 0 0 15em; /* Change 15em to your desired minimum width
        width: 10em;
        height: 100vh;
        overflow-y: auto;
        */
        background: #f4f4f8;
        padding: 1em;
        box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
    }

    #register-device-button {
        border: 1px solid #ccc;
        border-radius: 0.3em;
        padding: 0.5em;
        margin: 0.5em 0;
        background: white;
        width: 100%;
    }

    #register-device-button:hover {
        background-color: #d9b6c9;
        transform: scale(1.05);
    }

    h3 {
        margin: 0.5em 0;
    }

</style>
