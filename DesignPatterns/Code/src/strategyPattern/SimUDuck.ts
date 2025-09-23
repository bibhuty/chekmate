interface FlyBehaviour {
    fly(): string;
}

export class FlyWithWings implements FlyBehaviour {
    fly(): string {
        return "Fly with Wings";
    }
}

export class FlyNoWay implements FlyBehaviour {
    fly(): string {
        return "Can't fly";
    }

}

export abstract class Duck {
    protected constructor(private _flyBehaviour: FlyBehaviour) {}

    abstract display(): string;

    performFly(): string {
        return this._flyBehaviour.fly();
    }

    set flyBehaviour(flyBehaviour: FlyBehaviour) {
        this._flyBehaviour = flyBehaviour;
    }
}

export class MallardDuck extends Duck {
    constructor() {
        super(new FlyWithWings());
    }

    display(): string {
        return "MallardDuck";
    }
}

export class RedheadDuck extends Duck {
    constructor() {
        super(new FlyWithWings());
    }

    display(): string {
        return "RedheadDuck";
    }
}

export class RubberDuck extends Duck {
    constructor() {
        super(new FlyNoWay());
    }

    display(): string {
        return "RubberDuck";
    }
}

export class WoodenDuck extends Duck {
    constructor() {
        super(new FlyNoWay());
    }

    display(): string {
        return "WoodenDuck";
    }
}