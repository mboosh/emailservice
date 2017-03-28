/**
 * 
 */
var emailServiceApp = angular.module('EmailServiceApp', ["ngMessages"]);

var emailServiceController = emailServiceApp.controller('EmailServiceController', ['$scope', '$http', function($scope, $http) {  
  $scope.submitForm = function() {
    emailData = {
	  toEmail: $scope.toemail,
	  ccEmail: $scope.ccemail,
	  subject: $scope.subject,
	  content: $scope.content
    };	  
	  
	var res = $http.post('service', emailData);
	res.success(function(data, status, headers, config) {
		$scope.message = data;
	});
	res.error(function(data, status, headers, config) {
		alert("An Error Occured: " + JSON.stringify({data: data}));
	});
  };  
}]);