<script>
    export let device;
    export let imageUrl;
    export let title;
</script>

<div class="device-card">
    <img src={imageUrl} alt={title} class="device-image"/>
    <div class="device-info">
        <h3>{device.name}</h3>
        <p>{device.ipAddress}</p>
        <button class="configure-button">Configure</button>
    </div>

</div>

<style>
    .device-card {
        align-items: center;
        border: 1px solid #ccc;
        border-radius: 0.3em;
        padding: 0.5em;
        margin: 0.5em 0;
        background: white;
    }

    img {
        width: 80px;
        height: 70px;
    }

    .device-info {
        flex-grow: 1;
    }

    .configure-button {
        margin-top: 0.5em; /* Space above the button */
        width: 100%;
    }

</style>
