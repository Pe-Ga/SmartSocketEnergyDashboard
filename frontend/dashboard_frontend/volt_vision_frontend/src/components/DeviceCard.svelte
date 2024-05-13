<script>
    export let device;
    export let imageUrl;
    export let title;
</script>

<div class="device-card">
    <div class="device-info">
        <h3>{device.name}</h3>
        <p>{device.ipAddress}</p>
    </div>
    <img src={imageUrl} alt={title} class="device-image"/>
</div>

<style>
    .device-card {
        display: flex; /* Use flexbox for layout */
        align-items: center; /* Vertically center the items */
        border: 1px solid #ccc;
        border-radius: 0.3em;
        padding: 0.5em;
        margin: 0.5em 0;
        background: white;
    }

    .device-image {
        width: auto; /* Let the image adjust its width */
        height: 1.5em; /* Set the height to match the header */
        margin-left: 1em; /* Adjust the spacing between the image and text */
    }

    .device-info {
        flex-grow: 1; /* Allow the text to grow and fill available space */
    }
</style>
