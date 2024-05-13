<script>
    import { createEventDispatcher } from "svelte";

    const dispatch = createEventDispatcher();

    export let devices = [];
    let name = '';
    let ipAddress = '';

    function submitForm() {
        if (name.trim() === '' || ipAddress.trim() === '') {
            alert('Please fill all fields.');
            return;
        }

        // Simple IP address validation
        const ipRegex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        if (!ipRegex.test(ipAddress)) {
            alert('Invalid IP address.');
            return;
        }

        const isIpUsed = devices.some(device => device.ipAddress === ipAddress);
        if (isIpUsed) {
            alert('IP address is already in use.');
            return;
        }
        console.log({ name, ipAddress });
        dispatch('submit', { name, ipAddress });
    }
</script>

<div class="modal">
    <div class="modal-content">
        <div>{devices}</div>
        <span on:click={() => dispatch('close')}>Close</span>
        <h2>Register new device</h2>
            <input type="text" bind:value={name} placeholder="Device Name" />
            <input type="text" bind:value={ipAddress} placeholder="Device IP Address" />
        <button on:click={submitForm}>Submit</button>
    </div>
</div>

<style>
    .modal {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.5);
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .modal-content {
        background: white;
        padding: 20px;
        border-radius: 5px;
    }
    input, button {
        margin: 10px 0;
        padding: 10px;
        width: 100%;
    }
</style>
