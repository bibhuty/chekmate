import {
    CurrentConditionsDisplay,
    ForecastDisplay,
    StatisticsDisplay,
    WeatherStation
} from "../../src/ObserverPattern/WeatherORama";

describe('WeatherStation Observer Pattern', () => {
    let station: WeatherStation;

    beforeEach(() => {
        station = new WeatherStation();
    });

    test('should register and notify observers', () => {
        const mockObserver = { update: jest.fn() };
        station.registerObserver(mockObserver);
        station.setMeasurements(25, 65, 1013);

        expect(mockObserver.update).toHaveBeenCalledWith({ temperature: 25, humidity: 65, pressure: 1013 });
        expect(station.getObserversCount()).toBe(1);
    });

    test('should remove observers correctly', () => {
        const mockObserver = { update: jest.fn() };
        station.registerObserver(mockObserver);
        expect(station.getObserversCount()).toBe(1);

        station.removeObserver(mockObserver);
        expect(station.getObserversCount()).toBe(0);
    });

    test('CurrentConditionsDisplay should show current temperature and humidity', () => {
        const display = new CurrentConditionsDisplay(station);
        station.setMeasurements(30, 70, 1012);

        expect(display.display()).toBe('Current conditions: 30°C and 70% humidity');
    });

    test('StatisticsDisplay should track min, max and average temperature', () => {
        const stats = new StatisticsDisplay(station);

        station.setMeasurements(20, 65, 1012);
        station.setMeasurements(30, 70, 1012);
        station.setMeasurements(25, 60, 1012);

        expect(stats.display()).toBe('Avg/Max/Min temperature = 25.0/30/20');
    });

    test('ForecastDisplay should update based on pressure changes', () => {
        const forecast = new ForecastDisplay(station);

        station.setMeasurements(25, 65, 1015);
        expect(forecast.display()).toBe('Forecast: Improving weather on the way!');

        station.setMeasurements(25, 65, 1015);
        expect(forecast.display()).toBe('Forecast: More of the same');

        station.setMeasurements(25, 65, 1010);
        expect(forecast.display()).toBe('Forecast: Watch out for cooler, rainy weather');
    });

    test('multiple observers should all receive updates', () => {
        const display1 = new CurrentConditionsDisplay(station);
        const display2 = new StatisticsDisplay(station);

        const spy1 = jest.spyOn(display1, 'update');
        const spy2 = jest.spyOn(display2, 'update');

        station.setMeasurements(28, 60, 1011);

        expect(spy1).toHaveBeenCalled();
        expect(spy2).toHaveBeenCalled();
    });
});