type WeatherData = {
    temperature: number;
    humidity: number;
    pressure: number;
}
interface Observer{
    update(data:WeatherData):void;
}
interface Subject{
    registerObserver(o:Observer):void;
    removeObserver(o:Observer):void;
    notifyObservers():void;
}
interface Display{
    display():string;
}
export class WeatherStation implements Subject{
    private observers: Observer[] = [];
    private weatherData: WeatherData = { temperature: 0, humidity: 0, pressure: 0 };

    setMeasurements(temperature: number, humidity: number, pressure: number): void {
        this.weatherData = { temperature, humidity, pressure };
        this.notifyObservers();
    }
    notifyObservers(): void {
        this.observers.forEach(observer => observer.update(this.weatherData));
    }

    registerObserver(o: Observer): void {
        this.observers.push(o);
    }

    removeObserver(o: Observer): void {
        this.observers = this.observers.filter(observer => observer !== o);
    }
    getObserversCount(): number {
        return this.observers.length;
    }
}
export class CurrentConditionsDisplay implements Observer, Display {
    private temperature: number = 0;
    private humidity: number = 0;
    private weatherStation: Subject;
    constructor(private station: Subject) {
        this.weatherStation = station;
        this.weatherStation.registerObserver(this);
    }
    update(data: WeatherData): void {
        this.temperature = data.temperature;
        this.humidity = data.humidity;
        this.display();
    }
    display(): string {
        return `Current conditions: ${this.temperature}°C and ${this.humidity}% humidity`;
    }
}

export class StatisticsDisplay implements Observer, Display {
    private maxTemp: number = Number.MIN_VALUE;
    private minTemp: number = Number.MAX_VALUE;
    private tempSum: number = 0;
    private numReadings: number = 0;
    private weatherStation: Subject;
    constructor(private station: Subject) {
        this.weatherStation = station;
        this.weatherStation.registerObserver(this);
    }
    update(data: WeatherData): void {
        const temp = data.temperature;
        this.tempSum += temp;
        this.numReadings++;

        if (temp > this.maxTemp) {
            this.maxTemp = temp;
        }

        if (temp < this.minTemp) {
            this.minTemp = temp;
        }
        this.display();
    }
    display(): string {
        return `Avg/Max/Min temperature = ${(this.tempSum / this.numReadings).toFixed(1)}/${this.maxTemp}/${this.minTemp}`;
    }
}

export class ForecastDisplay implements Observer, Display {
    private lastPressure: number = 1013;
    private currentPressure: number = 1013;
    private weatherStation: Subject;
    constructor(private station: Subject) {
        this.weatherStation = station;
        this.weatherStation.registerObserver(this);
    }
    update(data: WeatherData): void {
        this.lastPressure = this.currentPressure;
        this.currentPressure = data.pressure;
        this.display();
    }
    display(): string {
        return "Forecast: " + (this.currentPressure > this.lastPressure ? "Improving weather on the way!" : (this.currentPressure === this.lastPressure ? "More of the same" : "Watch out for cooler, rainy weather"));
    }
}