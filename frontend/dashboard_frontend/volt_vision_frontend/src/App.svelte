<script>
    import Header from './components/Header.svelte';
    import DeviceSidebar from "./components/DeviceSidebar.svelte";
    import MainBody from "./components/MainBody.svelte";
    import Footer from "./components/Footer.svelte";
</script>

<div class="body">
    <header>
        <Header />
    </header>
    <aside>
        <DeviceSidebar />
    </aside>
    <main>
        <MainBody />
    </main>
    <footer>
        <Footer />
    </footer>
</div>

<style>
    .body {
        background-color: lightseagreen;
        broder-radius: 25px;
        display: grid;
        grid-template-areas:
        'header header header header'
        'aside main main main'
        'footer footer footer footer';
        gap: 0.5em;
    }

    aside {
        border-radius: 50px;
        height: 100%;
    }

    header, footer {
        grid-column: 1 / -1;
    }


</style>