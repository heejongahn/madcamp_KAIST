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

        // Make item
        for (i=0; i<data.results.length; i++) {
          var shop = data.results[i];

          var item = document.createElement('a');
          item.classList.add('result-item');
          item.innerHTML = shop.name + " | " + shop.formatted_address;

          item.dataset.shopname = shop.name;
          item.dataset.address = shop.formatted_address;
          item.dataset.lon = shop.geometry.location.lng;
          item.dataset.lat = shop.geometry.location.lat;
          item.onclick = function() {
            document.getElementById('address').value = this.dataset.address;
            document.getElementById('shopname').value = this.dataset.shopname;
            document.getElementById('lon').value = this.dataset.lon;
            document.getElementById('lat').value = this.dataset.lat;
            $("#search-address").modal('toggle');
          }

          searchResult.appendChild(item);
        }

      },
      error: function(req, status, data) {
        console.log(data);
      }});
  });

});
