<script>
    import { createEventDispatcher } from "svelte";

    export let device;

    const dispatch = createEventDispatcher();

    let startTime = "";
    let endTime = "";

    function handleConfigChange(e) {
        if (e.target.name === "startTime") {
            startTime = e.target.value;
        } else if (e.target.name === "endTime") {
            endTime = e.target.value;
        }
    }

    function saveConfig() {
        // Here, you can implement the logic to save the start and end times for the device
        console.log(`Device ${device.name} should be switched off from ${startTime} to ${endTime}`);
        closeModal();
    }

    function closeModal() {
        dispatch("close");
    }
</script>

<div class="modal-overlay">
    <div class="modal">
        <h3>{device.name}</h3>
        <h3>Schedule Device Plan</h3>
        <div class="config-form">
            <label>
                Start Time:
                <input type="time" name="startTime" bind:value={startTime} on:input={handleConfigChange} />
            </label>
            <label>
                End Time:
                <input type="time" name="endTime" bind:value={endTime} on:input={handleConfigChange} />
            </label>
            <div class="button-group">
                <button on:click={saveConfig}>Save</button>
                <button on:click={closeModal}>Cancel</button>
            </div>
        </div>
    </div>
</div>

<style>
    .modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 999;
    }

    .modal {
        background-color: white;
        padding: 1em;
        border-radius: 0.5em;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    }

    .config-form {
        margin-top: 1em;
    }

    .config-form label {
        display: block;
        margin-bottom: 0.5em;
    }

    .config-form input {
        margin-left: 0.5em;
    }

    .button-group {
        display: flex;
        justify-content: flex-end;
        margin-top: 1em;
    }

    .button-group button {
        margin-left: 0.5em;
    }
</style>