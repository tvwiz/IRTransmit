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
    },

    hasIrEmitter: function(successCallback, errorCallback) {
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "hasIrEmitter",
	    [{
	    }]
	);
    },

    getCarrierFrequencies: function(successCallback, errorCallback) {
	cordova.exec(successCallback,
	    errorCallback,
	    "IRTransmit",
	    "getCarrierFrequencies",
	    [{
	    }]
	);
    }
}

module.exports = irtransmit;
