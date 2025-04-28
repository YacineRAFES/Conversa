export function formatDate(dateString) {
    const date = new Date(dateString);
    const now = new Date();

    const isAujourdhui = date.toDateString() === now.toDateString();
    const hier = new Date();
    hier.setDate(now.getDate() - 1);
    const isHier = date.toDateString() === hier.toDateString();

    const optionsTime = { hour: '2-digit', minute: '2-digit' };
    const timeFormatted = date.toLocaleTimeString([], optionsTime);

    if (isAujourdhui) {
        return `Aujourd'hui ${timeFormatted}`;
    } else if (isHier) {
        return `Hier ${timeFormatted}`;
    } else {
        const optionsDate = { day: '2-digit', month: '2-digit', year: 'numeric' };
        const dateFormatted = date.toLocaleDateString([], optionsDate);
        return `${dateFormatted} ${timeFormatted}`;
    }
}