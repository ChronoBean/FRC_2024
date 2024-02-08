public class TankDrive(SCurve) extends TimedRobot {
  private DifferentialDrive m_robotDrive;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  private final PWMSparkMax m_leftMotor = new PWMSparkMax(0);
  private final PWMSparkMax m_rightMotor = new PWMSparkMax(1);

  private double currentLeftSpeed = 0.0;
  private double currentRightSpeed = 0.0;

  @Override
  public void robotInit() {
    // Instantiate objects
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);

    m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
    m_rightMotor.setInverted(true);

    SendableRegistry.addChild(m_robotDrive, m_leftMotor);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor);
  }

  @Override
  public void teleopPeriodic() {
    // Get joystick inputs
    double leftStickInput = -m_leftStick.getY();
    double rightStickInput = -m_rightStick.getY();

    // Set maximum change in speed per iteration
    double maxChange = 0.02;

    // Acceleration
    if (leftStickInput > currentLeftSpeed) {
      currentLeftSpeed = Math.min(currentLeftSpeed + maxChange, leftStickInput);
    } else {
      currentLeftSpeed = Math.max(currentLeftSpeed - maxChange, leftStickInput);
    }

    if (rightStickInput > currentRightSpeed) {
      currentRightSpeed = Math.min(currentRightSpeed + maxChange, rightStickInput);
    } else {
      currentRightSpeed = Math.max(currentRightSpeed - maxChange, rightStickInput);
    }

    // Update motor speeds
    m_robotDrive.tankDrive(currentLeftSpeed, currentRightSpeed);
  }
}
