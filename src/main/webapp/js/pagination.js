function setPagination(page) {
    if (page.totalPages === 1 || page.numberOfElements === 0) {
        document.getElementById('next-page').style.display = 'none';
        document.getElementById('prev-page').style.display = 'none';
    } else {
        if (page.number === 0) {
            document.getElementById('next-page').style.display = 'block';
            document.getElementById('prev-page').style.display = 'none';
        } else if (page.last) {
            document.getElementById('next-page').style.display = 'none';
            document.getElementById('prev-page').style.display = 'block';
        } else {
            document.getElementById('prev-page').style.display = 'block';
            document.getElementById('next-page').style.display = 'block';
        }
    }
}