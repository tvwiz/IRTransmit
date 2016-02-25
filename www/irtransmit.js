var irtransmit = {

    transmit: function(frequency, signal,successCallback, errorCallback){
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "transmit",
	    [{
		"frequency": frequency,
		"patten": pattern
	    }]
	);
    }
}

module.exports = irtransmit;
