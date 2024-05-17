<script>
    import DeviceScheduleForm from "./DeviceScheduleForm.svelte";
    export let device;
    export let imageUrl;
    export let title;

    function getPowerStatusColor() {
        return device.powerOn ? 'green' : 'red';
    }

    function getPowerStatusText() {
        return device.powerOn ? 'ON' : 'OFF';
    }

    let showScheduleModal = false;

    function toggleScheduleModal() {
        showScheduleModal = !showScheduleModal;
    }
</script>

<div class="device-card">
    <div class="device-header">
        <img src={imageUrl} alt={title} class="device-image" />
        <h3 class="device-name">
            Device: {device.name}
        </h3>
    </div>
    <div class="device-info">
        <p>IP: {device.ipAddress}</p>
        <button class="power-status" style="background-color: {getPowerStatusColor()}; width: 100%;">
            {getPowerStatusText()}
        </button>
        <button class="configure-button" on:click={toggleScheduleModal}>Configure</button>
    </div>
</div>

{#if showScheduleModal}
    <DeviceScheduleForm {device} on:close={toggleScheduleModal} />
{/if}

<style>
    .device-card {
        align-items: center;
        border: 1px solid #ccc;
        border-radius: 0.3em;
        padding: 0.5em;
        margin: 0.5em 0;
        background: white;
    }
    .device-header {
        display: flex;
        align-items: center;
        margin-bottom: 0.5em;
    }
    .device-image {
        width: 40px;
        height: 35px;
        margin-right: 0.5em;
    }
    .device-name {
        margin: 0;
        flex-grow: 1;
    }
    .power-status {
        width: 100%;
        padding: 0.5em;
        font-weight: bold;
        color: white;
        border: none;
        border-radius: 0.3em;
        margin-bottom: 0.5em;
    }
    .device-info {
        flex-grow: 1;
    }
    .configure-button {
        width: 100%;
    }
</style>