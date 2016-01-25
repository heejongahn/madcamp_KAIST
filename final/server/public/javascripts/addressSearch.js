$(document).ready(function() {

  $('#search-button').click(function() {
    var query = $('#search-query').val();

    $.ajax({
      url: '/address',
      dataType: 'json',
      data: {'query': query},
      success: function(data, status, req) {
        console.log(data.results);
        var searchResult = document.getElementById('search-result');
        searchResult.innerHTML = "";

        // Make list
        for (i=0; i<data.results.length; i++) {
          var shop = data.results[i];

          var list = document.createElement('a');
          list.classList.add('result-item');
          list.innerHTML = shop.name + " | " + shop.formatted_address;

          list.dataset.name = shop.name;
          list.dataset.lon = shop.geometry.location.lng;
          list.dataset.lat = shop.geometry.location.lat;

          searchResult.appendChild(list);
        }

      },
      error: function(req, status, data) {
        console.log(data);
      }});
  });
});
